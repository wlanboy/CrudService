package com.wlanboy.demo.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wlanboy.demo.hashing.HashTest;
import com.wlanboy.demo.model.HelloParameterMap;
import com.wlanboy.demo.model.HelloParameters;
import com.wlanboy.demo.model.SimpleObject;
import com.wlanboy.demo.service.MapsService;

public class MapControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MapsService mapsService;

    @InjectMocks
    private MapController mapController;
    
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(mapController)
                .build();
    }
    
    @Test
    public void test_get_all_success() throws Exception {
        List<SimpleObject> testdata = Arrays.asList(
                new SimpleObject(1, HashTest.teststring,HashTest.hashstring),
                new SimpleObject(2, HashTest.teststring2,HashTest.hashstring2));
        
        when(mapsService.findAllObjects()).thenReturn(testdata);
        
        mockMvc.perform(get("/map"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].value").value(HashTest.teststring))
                .andExpect(jsonPath("$[0].hash").value(HashTest.hashstring))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].value").value(HashTest.teststring2))
                .andExpect(jsonPath("$[1].hash").value(HashTest.hashstring2));
        verify(mapsService, times(1)).findAllObjects();
        verifyNoMoreInteractions(mapsService);
    }
    
    @Test
    public void test_get_one_success() throws Exception {
    	long id = 1;
        SimpleObject testdata = new SimpleObject(id, HashTest.teststring,HashTest.hashstring);
        
        when(mapsService.searchObjectById(id)).thenReturn(testdata);
        
        mockMvc.perform(get("/map/"+id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.value").value(HashTest.teststring))
                .andExpect(jsonPath("$.hash").value(HashTest.hashstring));
        
        verify(mapsService, times(1)).searchObjectById(id);
        verifyNoMoreInteractions(mapsService);
    }   
    
    @Test
    public void test_get_one_404() throws Exception {
    	long id = 999;
        
        when(mapsService.searchObjectById(id)).thenReturn(null);
        
        mockMvc.perform(get("/map/"+id))
                .andExpect(status().isNotFound());
        
        verify(mapsService, times(1)).searchObjectById(id);
        verifyNoMoreInteractions(mapsService);
    }     
    
    @Test
    public void test_create_map_success() throws Exception {
    	long id = 1;
        SimpleObject testdatadb = new SimpleObject(id, HashTest.teststring,HashTest.hashstring);
        SimpleObject testdata = new SimpleObject(HashTest.teststring,null);
        HelloParameterMap testrequest = new HelloParameterMap(id, HashTest.teststring,HashTest.hashstring);
        
        when(mapsService.searchSimpleObjectByNameOrValue(testdata)).thenReturn(null);
        when(mapsService.SaveObject(testdata)).thenReturn(testdatadb);
        
        mockMvc.perform(
        		 
                post("/map")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testrequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.value").value(HashTest.teststring))
                .andExpect(jsonPath("$.hash").value(HashTest.hashstring));                
        
        verify(mapsService, times(1)).searchSimpleObjectByNameOrValue(testdata);
        verify(mapsService, times(1)).SaveObject(testdata);
        verifyNoMoreInteractions(mapsService);
    }
    
    @Test
    public void test_create_map_conflict() throws Exception {
    	long id = 1;
        SimpleObject testdatadb = new SimpleObject(id, HashTest.teststring,HashTest.hashstring);
        SimpleObject testdata = new SimpleObject(HashTest.teststring,null);
        HelloParameterMap testrequest = new HelloParameterMap(HashTest.teststring,HashTest.hashstring);
        
        when(mapsService.searchSimpleObjectByNameOrValue(testdata)).thenReturn(testdatadb);
        
        mockMvc.perform(
        		 
                post("/map")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testrequest)))
                .andExpect(status().isConflict());
        
        verify(mapsService, times(1)).searchSimpleObjectByNameOrValue(testdata);
        verifyNoMoreInteractions(mapsService);
    }   
    
    @Test
    public void test_update_map_success() throws Exception {
    	long id = 1;
        SimpleObject testdata = new SimpleObject(id, HashTest.teststring,HashTest.hashstring);
        HelloParameterMap testrequest = new HelloParameterMap(id, HashTest.teststring,HashTest.hashstring);
        
        when(mapsService.searchObjectById(id)).thenReturn(testdata);
        when(mapsService.SaveObject(testdata)).thenReturn(testdata);
        
        mockMvc.perform(
        		 
                put("/map/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testrequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.value").value(HashTest.teststring))
                .andExpect(jsonPath("$.hash").value(HashTest.hashstring));                
        
        verify(mapsService, times(1)).searchObjectById(id);
        verify(mapsService, times(1)).SaveObject(testdata);
        verifyNoMoreInteractions(mapsService);
    }    
    
    @Test
    public void test_update_map_404() throws Exception {
    	long id = 1;
        HelloParameters testrequest = new HelloParameters(id, HashTest.teststring,HashTest.hashstring);
        
        when(mapsService.searchObjectById(id)).thenReturn(null);
        
        mockMvc.perform(
        		 
                put("/map/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testrequest)))
                .andExpect(status().isNotFound());                
        
        verify(mapsService, times(1)).searchObjectById(id);
        verifyNoMoreInteractions(mapsService);
    }  
 
    @Test
    public void test_delete_map_success() throws Exception {
    	long id = 1;
    	SimpleObject testdata = new SimpleObject(id, HashTest.teststring,HashTest.hashstring);
        when(mapsService.searchObjectById(id)).thenReturn(testdata);
        //doNothing(vorgangsService.deleteSimpleObject(id));
        
        mockMvc.perform(
                delete("/map/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());                
        
        verify(mapsService, times(1)).searchObjectById(id);
        verify(mapsService, times(1)).deleteSimpleObject(id);
        verifyNoMoreInteractions(mapsService);
    }    
    
    @Test
    public void test_delete_map_404() throws Exception {
    	long id = 1;
        when(mapsService.searchObjectById(id)).thenReturn(null);
        
        mockMvc.perform(
                delete("/map/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());                
        
        verify(mapsService, times(1)).searchObjectById(id);
        verifyNoMoreInteractions(mapsService);
    }     
}
