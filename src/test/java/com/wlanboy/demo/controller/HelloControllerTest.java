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
import com.wlanboy.demo.model.HelloParameters;
import com.wlanboy.demo.model.Vorgang;
import com.wlanboy.demo.service.VorgangsService;

public class HelloControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VorgangsService vorgangsService;

    @InjectMocks
    private HelloController helloController;
    
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(helloController)
                .build();
    }
    
    @Test
    public void test_get_all_success() throws Exception {
        List<Vorgang> testdata = Arrays.asList(
                new Vorgang(1, "Test1","ok"),
                new Vorgang(2, "Test2","ok"));
        
        when(vorgangsService.findAllVorgaenge()).thenReturn(testdata);
        
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].identifier").value(1))
                .andExpect(jsonPath("$[0].target").value("Test1"))
                .andExpect(jsonPath("$[0].status").value("ok"))
                .andExpect(jsonPath("$[1].identifier").value(2))
                .andExpect(jsonPath("$[1].target").value("Test2"))
                .andExpect(jsonPath("$[1].status").value("ok"));
        verify(vorgangsService, times(1)).findAllVorgaenge();
        verifyNoMoreInteractions(vorgangsService);
    }
    
    @Test
    public void test_get_one_success() throws Exception {
    	long id = 1;
        Vorgang testdata = new Vorgang(id, "Test1","ok");
        
        when(vorgangsService.searchVorgangById(id)).thenReturn(testdata);
        
        mockMvc.perform(get("/hello/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.identifier").value(id))
                .andExpect(jsonPath("$.target").value("Test1"))
                .andExpect(jsonPath("$.status").value("ok"));
        
        verify(vorgangsService, times(1)).searchVorgangById(id);
        verifyNoMoreInteractions(vorgangsService);
    }   
    
    @Test
    public void test_get_one_404() throws Exception {
    	long id = 999;
        
        when(vorgangsService.searchVorgangById(id)).thenReturn(null);
        
        mockMvc.perform(get("/hello/999"))
                .andExpect(status().isNotFound());
        
        verify(vorgangsService, times(1)).searchVorgangById(id);
        verifyNoMoreInteractions(vorgangsService);
    }     
    
    @Test
    public void test_create_hello_success() throws Exception {
    	long id = 1;
        Vorgang testdatadb = new Vorgang(id, "Test1","ok");
        Vorgang testdata = new Vorgang("Test1","ok");
        HelloParameters testrequest = new HelloParameters(id, "Test1","ok");
        
        when(vorgangsService.searchVorgangById(id)).thenReturn(null);
        when(vorgangsService.searchVorgangByNameAndStatus(testdata)).thenReturn(null);
        when(vorgangsService.SaveVorgang(testdata)).thenReturn(testdatadb);
        //doNothing().when(vorgangsService).create(user);
        
        mockMvc.perform(
        		 
                post("/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testrequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.identifier").value(id))
                .andExpect(jsonPath("$.target").value("Test1"))
                .andExpect(jsonPath("$.status").value("ok"));                
        
        verify(vorgangsService, times(1)).searchVorgangByNameAndStatus(testdata);
        verify(vorgangsService, times(1)).SaveVorgang(testdata);
        verifyNoMoreInteractions(vorgangsService);
    }
    
    @Test
    public void test_create_hello_conflict() throws Exception {
    	long id = 1;
        Vorgang testdatadb = new Vorgang(id, "Test1","ok");
        Vorgang testdata = new Vorgang("Test1","ok");
        HelloParameters testrequest = new HelloParameters(id, "Test1","ok");
        
        when(vorgangsService.searchVorgangByNameAndStatus(testdata)).thenReturn(testdatadb);
        
        mockMvc.perform(
        		 
                post("/hello")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testrequest)))
                .andExpect(status().isConflict());
        
        verify(vorgangsService, times(1)).searchVorgangByNameAndStatus(testdata);
        verifyNoMoreInteractions(vorgangsService);
    }   
    
    @Test
    public void test_update_hello_success() throws Exception {
    	long id = 1;
        Vorgang testdata = new Vorgang(id, "Test1","ok");
        HelloParameters testrequest = new HelloParameters(id, "Test1","ok");
        
        when(vorgangsService.searchVorgangById(id)).thenReturn(testdata);
        when(vorgangsService.SaveVorgang(testdata)).thenReturn(testdata);
        
        mockMvc.perform(
        		 
                put("/hello/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testrequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identifier").value(id))
                .andExpect(jsonPath("$.target").value("Test1"))
                .andExpect(jsonPath("$.status").value("ok"));                
        
        verify(vorgangsService, times(1)).searchVorgangById(id);
        verify(vorgangsService, times(1)).SaveVorgang(testdata);
        verifyNoMoreInteractions(vorgangsService);
    }    
    
    @Test
    public void test_update_hello_404() throws Exception {
    	long id = 1;
        HelloParameters testrequest = new HelloParameters(id, "Test1","ok");
        
        when(vorgangsService.searchVorgangById(id)).thenReturn(null);
        
        mockMvc.perform(
        		 
                put("/hello/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testrequest)))
                .andExpect(status().isNotFound());                
        
        verify(vorgangsService, times(1)).searchVorgangById(id);
        verifyNoMoreInteractions(vorgangsService);
    }  
 
    @Test
    public void test_delete_hello_success() throws Exception {
    	long id = 1;
    	Vorgang testdata = new Vorgang(id, "Test1","ok");
        when(vorgangsService.searchVorgangById(id)).thenReturn(testdata);
        //doNothing(vorgangsService.deleteVorgang(id));
        
        mockMvc.perform(
                delete("/hello/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());                
        
        verify(vorgangsService, times(1)).searchVorgangById(id);
        verify(vorgangsService, times(1)).deleteVorgang(id);
        verifyNoMoreInteractions(vorgangsService);
    }    
    
    @Test
    public void test_delete_hello_404() throws Exception {
    	long id = 1;
        when(vorgangsService.searchVorgangById(id)).thenReturn(null);
        
        mockMvc.perform(
                delete("/hello/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());                
        
        verify(vorgangsService, times(1)).searchVorgangById(id);
        verifyNoMoreInteractions(vorgangsService);
    }     
}
