/*
 * Copyright 2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.release.projectservice;

import org.springframework.data.release.model.Project;

/**
 * Interface to abstract the actual interaction with the Projects service server.
 *
 * @author Oliver Gierke
 * @author Mark Paluch
 */
public interface ProjectService {

	/**
	 * Returns the project metadata for the given {@link MaintainedVersion}.
	 *
	 * @param version must not be {@literal null}.
	 * @return
	 */
	String getProjectMetadata(MaintainedVersion version);

	/**
	 * Returns all project release metadata for the given {@link Project}.
	 *
	 * @param project must not be {@literal null}.
	 * @return
	 */
	String getProjectMetadata(Project project);

	/**
	 * Updates the project metadata for the given {@link Project} to the given {@link MaintainedVersions}.
	 *
	 * @param project must not be {@literal null}.
	 * @param versions must not be {@literal null}.
	 */
	void updateProjectMetadata(Project project, MaintainedVersions versions);

	/**
	 * Verify correct authentication setup.
	 */
	void verifyAuthentication();
}
