/*
 * Copyright 2020-2022 the original author or authors.
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
package org.springframework.data.release.model;

import lombok.Value;

import java.util.Locale;

/**
 * Value object providing documentation links.
 *
 * @author Mark Paluch
 */
@Value(staticConstructor = "of")
public class DocumentationMetadata {

	private static String docsBase = "https://docs.spring.io/spring-data/%s/docs/%s";
	private static String docs = docsBase.concat("/reference/html/");
	private static String javadoc = docsBase.concat("/api/");

	Project project;
	ArtifactVersion version;
	boolean isCurrent;

	/**
	 * Returns the JavaDoc URL for non-snapshot versions and not the build project.
	 *
	 * @return
	 */
	public String getApiDocUrl() {

		if (version.isSnapshotVersion()) {
			return "";
		}

		if (Projects.BUILD.equals(project)) { // Report Commons Docs for Spring Data Build
			return String.format(javadoc, getProjectName(Projects.COMMONS), getDocumentationVersion());
		}

		return String.format(javadoc, getProjectName(project), getDocumentationVersion());
	}

	private String getProjectName(Project project) {

		if (project == Projects.RELATIONAL) {
			return "jdbc";
		}

		return project.getName().toLowerCase(Locale.US);
	}

	/**
	 * Returns the reference documentation URL for non-snapshot versions and not the build project.
	 *
	 * @return
	 */
	public String getReferenceDocUrl() {

		if (version.isSnapshotVersion()) {
			return "";
		}

		if (Projects.BUILD.equals(project)) { // Report Commons Docs for Spring Data Build
			return String.format(docs, getProjectName(Projects.COMMONS), getDocumentationVersion());
		}

		return String.format(docs, getProjectName(project), getDocumentationVersion());
	}

	public String getVersionOrTrainName(Train train) {

		if (Projects.BUILD.equals(project)) {

			if (train.usesCalver()) {

				if (version.isBugFixVersion() || version.isReleaseVersion()) {
					return train.getCalver().withBugfix(version.getVersion().getBugfix()).toMajorMinorBugfix();
				}

				return String.format("%s-%s",
						train.getCalver().withBugfix(version.getVersion().getBugfix()).toMajorMinorBugfix(),
						version.getReleaseTrainSuffix());
			}

			return String.format("%s-%s", train.getName(),
					version.isReleaseVersion() && !version.isBugFixVersion() ? "RELEASE" : version.getReleaseTrainSuffix());
		}

		return version.toString();
	}

	public String getDocumentationVersion() {
		return isCurrent ? "current" : version.toString();
	}

}
