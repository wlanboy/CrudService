package com.wlanboy.demo.integration;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.wlanboy.demo.controller.HelloController;
import com.wlanboy.demo.controller.MapController;
import com.wlanboy.demo.hashing.HashTest;
import com.wlanboy.demo.model.SimpleObject;
import com.wlanboy.demo.model.Vorgang;
import com.wlanboy.demo.service.MapsService;
import com.wlanboy.demo.service.VorgangsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
public class IntegrationSecurityTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
    	mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    
    @Mock
    private VorgangsService vorgangsService;

    @InjectMocks
    private HelloController helloController;
    
    @Mock
    private MapsService mapsService;

    @InjectMocks
    private MapController mapController;
    
    @Test
    public void test_get_all_hello_success() throws Exception {
        List<Vorgang> testdata = Arrays.asList(
                new Vorgang(1, "Test1","ok"),
                new Vorgang(2, "Test2","ok"));
        
        when(vorgangsService.findAllVorgaenge()).thenReturn(testdata);
        
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
    
    @Test
    public void test_get_all_object_no_access_success() throws Exception {
        List<SimpleObject> testdata = Arrays.asList(
                new SimpleObject(1, HashTest.teststring,HashTest.hashstring),
                new SimpleObject(2, HashTest.teststring2,HashTest.hashstring2));
        
        when(mapsService.findAllObjects()).thenReturn(testdata);
        
        mockMvc.perform(get("/map"))
                .andExpect(status().isUnauthorized());
    }
    
    @Test
    @WithMockUser(username = "user", password = "user", roles = {"SIMPLEOBJECT"})
    public void test_get_all_object_success() throws Exception {
        List<SimpleObject> testdata = Arrays.asList(
                new SimpleObject(1, HashTest.teststring,HashTest.hashstring),
                new SimpleObject(2, HashTest.teststring2,HashTest.hashstring2));
        
        when(mapsService.findAllObjects()).thenReturn(testdata);
        
        mockMvc.perform(get("/map"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }
    
    @Test
    @WithMockUser(username = "usermod", password = "user", roles = {"SIMPLEOBJECT","MODOBJECT"})
    public void test_delete_map_404() throws Exception {
    	long id = 1;
        when(mapsService.searchObjectById(id)).thenReturn(null);
        
        mockMvc.perform(
                delete("/map/1"))
                .andExpect(status().isNotFound());                
    }  
    
    @Test
    @WithMockUser(username = "user", password = "user", roles = {"SIMPLEOBJECT"})
    public void test_delete_map_no_access() throws Exception {
    	long id = 1;
        when(mapsService.searchObjectById(id)).thenReturn(null);
        
        mockMvc.perform(
                delete("/map/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());                
    }   
     
}
