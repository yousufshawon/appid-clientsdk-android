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

package com.ibm.bluemix.appid.android.api.userattributes;

import android.support.annotation.NonNull;

import com.ibm.bluemix.appid.android.api.tokens.AccessToken;

public interface UserAttributeManager {
	void setAttribute(@NonNull String name, @NonNull String value, UserAttributeResponseListener listener);
	void setAttribute(@NonNull String name, @NonNull String value, AccessToken accessToken, UserAttributeResponseListener listener);

	void getAttribute(@NonNull String name, UserAttributeResponseListener listener);
	void getAttribute(@NonNull String name, AccessToken accessToken, UserAttributeResponseListener listener);

	void deleteAttribute(@NonNull String name, UserAttributeResponseListener listener);
	void deleteAttribute(@NonNull String name, AccessToken accessToken, UserAttributeResponseListener listener);

	void getAllAttributes(@NonNull UserAttributeResponseListener listener);
	void getAllAttributes(AccessToken accessToken, @NonNull UserAttributeResponseListener listener);
}
