/*
	Copyright 2017 IBM Corp.
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	http://www.apache.org/licenses/LICENSE-2.0
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/
package com.ibm.bluemix.appid.android.internal.loginwidget;

import android.app.Activity;

import com.ibm.bluemix.appid.android.api.AuthorizationException;
import com.ibm.bluemix.appid.android.api.AuthorizationListener;
import com.ibm.bluemix.appid.android.api.tokens.AccessToken;
import com.ibm.bluemix.appid.android.api.tokens.IdentityToken;
import com.ibm.bluemix.appid.android.internal.OAuthManager;
import com.ibm.bluemix.appid.android.internal.authorizationmanager.AuthorizationManager;
import com.ibm.bluemix.appid.android.internal.tokenmanager.TokenManager;
import com.ibm.bluemix.appid.android.internal.tokens.AccessTokenImpl;
import com.ibm.bluemix.appid.android.testing.helpers.Consts;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoginWidgetImpl_Test {

    @Mock
    private OAuthManager oAuthManager;
    @Mock
    private AuthorizationManager mockAuthManager;
    @Mock
    private TokenManager mockTokenManager;

    private static final AccessToken expectedAccessToken = new AccessTokenImpl(Consts.ACCESS_TOKEN);
    private LoginWidgetImpl loginWidget;
    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
        loginWidget = new LoginWidgetImpl(oAuthManager);
        when(oAuthManager.getAuthorizationManager()).thenReturn(mockAuthManager);
        when(oAuthManager.getTokenManager()).thenReturn(mockTokenManager);
        when(mockTokenManager.getLatestAccessToken()).thenReturn(expectedAccessToken);
    }

    @Test
    public void launch_test(){

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                AccessToken accessToken = (AccessToken) args[1];
                AuthorizationListener authorizationListener = (AuthorizationListener) args[2];
                authorizationListener.onAuthorizationSuccess(accessToken, null);
                return null;
            }
        }).when(mockAuthManager).launchAuthorizationUI(any(Activity.class), any(AccessToken.class),any(AuthorizationListener.class));


        loginWidget.launch(Mockito.mock(Activity.class), new AuthorizationListener() {
            @Override
            public void onAuthorizationCanceled() {
                fail("should get to onAuthorizationSuccess");
            }

            @Override
            public void onAuthorizationFailure(AuthorizationException exception) {
                fail("should get to onAuthorizationSuccess");
            }

            @Override
            public void onAuthorizationSuccess(AccessToken accessToken, IdentityToken identityToken) {
                assertEquals(accessToken, expectedAccessToken);
            }
        }, null);
    }

    @Test
    public void launch_test_with_access_token(){

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                AccessToken accessToken = (AccessToken) args[1];
                AuthorizationListener authorizationListener = (AuthorizationListener) args[2];
                authorizationListener.onAuthorizationSuccess(accessToken, null);
                return null;
            }
        }).when(mockAuthManager).launchAuthorizationUI(any(Activity.class), any(AccessToken.class),any(AuthorizationListener.class));


        loginWidget.launch(Mockito.mock(Activity.class), new AuthorizationListener() {
            @Override
            public void onAuthorizationCanceled() {
                fail("should get to onAuthorizationSuccess");
            }

            @Override
            public void onAuthorizationFailure(AuthorizationException exception) {
                fail("should get to onAuthorizationSuccess");
            }

            @Override
            public void onAuthorizationSuccess(AccessToken accessToken, IdentityToken identityToken) {
                assertEquals(accessToken.getRaw(), expectedAccessToken.getRaw());
            }
        }, expectedAccessToken.getRaw());
    }
}
