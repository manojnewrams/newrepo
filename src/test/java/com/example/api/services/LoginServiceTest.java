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
public class LoginServiceTest {

    @Mock
    private LoginImplementation LoginImplementation;
    private Response Response;
    private Login Login ;

    @Before
    public void setup(){
        Login  = new Login();
        Response = new Response();
    }

    @Test
    public void access() {
        Mockito.when(LoginImplementation.access(Mockito.any(Login.class))).thenReturn(Response);
        Object data = LoginImplementation.access(Login);
        assertNotNull(data);
        Assert.assertEquals(Response, data);
        verify(LoginImplementation).access(Login);
        verifyNoMoreInteractions(LoginImplementation);

    }

}