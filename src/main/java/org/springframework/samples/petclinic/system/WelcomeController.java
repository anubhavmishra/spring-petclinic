/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.system;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.configcat.*;

@Controller
class WelcomeController {

	@Value("${cache.enabled}")
	String cacheEnabled;

	@Value("${cache.endpoint}")
	String cacheEndpoint;

	@Value("${configcat.key}")
	String configCatSDKKey;

	@Value("${configcat.maintmode.key}")
	String maintenanceModeFeatureFlagName;

	@GetMapping("/")
	public String welcome(Model model) {
		if (cacheEnabled.equals("true") && cacheEndpoint != null) {
			model.addAttribute("endpoint", cacheEndpoint);
		}

		ConfigCatClient client = ConfigCatClient.get(configCatSDKKey);
		boolean maintenanceMode = client.getValue(Boolean.class, maintenanceModeFeatureFlagName, false);
		System.out.println("maintenanceMode: " + maintenanceMode);
		if (maintenanceMode) {
			return "maintenance";
		} else {
			return "welcome";
		}
	}

}
