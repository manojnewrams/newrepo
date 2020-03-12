package com.example.api.controller;

import com.example.api.domain.Login;
import com.example.api.domain.RegisterUser;
import com.example.api.domain.Response;
import com.example.api.services.LoginImplementation;
import com.example.api.services.LoginService;
import com.example.api.services.ProfileImplementation;
import com.example.api.services.RegistryImplementation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@RunWith(MockitoJUnitRunner.class)
public class ControllerTest {

    @Mock
    private ProfileImplementation ProfileImplementation;
    public LoginImplementation LoginImplementation;
    public RegistryImplementation RegistryImplementation;
    private Response Response;



    @Before
    public void setup(){
         Response  = new Response();
        RegisterUser registerUser = new RegisterUser();
    }

    @Test
    public void getProfile() {
        Mockito.when(ProfileImplementation.getProfile(Mockito.anyString())).thenReturn(Response);
        Object data = ProfileImplementation.getProfile("a");
        assertNotNull(data);
        Assert.assertEquals(Response, data);
        verify(ProfileImplementation).getProfile(Mockito.anyString());
        verifyNoMoreInteractions(ProfileImplementation);
    }




}