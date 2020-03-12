package com.example.api.services;


import com.example.api.domain.Login;
import com.example.api.domain.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;


@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    @Mock
    private ProfileImplementation ProfileImplementation;
    private Response Response ;

    @Before
    public void setup(){
        Response = new Response();
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