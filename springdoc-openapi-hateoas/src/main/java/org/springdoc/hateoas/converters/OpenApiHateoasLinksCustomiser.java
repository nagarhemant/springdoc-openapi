/*
 *
 *  *
 *  *  *
 *  *  *  * Copyright 2019-2022 the original author or authors.
 *  *  *  *
 *  *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  *  * you may not use this file except in compliance with the License.
 *  *  *  * You may obtain a copy of the License at
 *  *  *  *
 *  *  *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *  *  *
 *  *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  *  * See the License for the specific language governing permissions and
 *  *  *  * limitations under the License.
 *  *  *
 *  *
 *
 */

package org.springdoc.hateoas.converters;

import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.core.converter.ResolvedSchema;
import io.swagger.v3.core.filter.SpecFilter;
import io.swagger.v3.core.util.AnnotationsUtils;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.MapSchema;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.customizers.OpenApiCustomiser;

import org.springframework.hateoas.Link;

/**
 * The type Open api hateoas links customiser.
 * @author bnasslahsen
 */
public class OpenApiHateoasLinksCustomiser extends SpecFilter implements OpenApiCustomiser {

	/**
	 * The Spring doc config properties.
	 */
	private final SpringDocConfigProperties springDocConfigProperties;

	/**
	 * Instantiates a new Open api hateoas links customiser.
	 *
	 * @param springDocConfigProperties the spring doc config properties
	 */
	public OpenApiHateoasLinksCustomiser(SpringDocConfigProperties springDocConfigProperties) {
		this.springDocConfigProperties = springDocConfigProperties;
	}

	@Override
	public void customise(OpenAPI openApi) {
		ResolvedSchema resolvedLinkSchema = ModelConverters.getInstance()
				.resolveAsResolvedSchema(new AnnotatedType(Link.class));
		openApi
				.schema("Link", resolvedLinkSchema.schema)
				.schema("Links", new MapSchema()
						.additionalProperties(new StringSchema())
						.additionalProperties(new ObjectSchema().$ref(AnnotationsUtils.COMPONENTS_REF + "Link")));
		if (springDocConfigProperties.isRemoveBrokenReferenceDefinitions())
			this.removeBrokenReferenceDefinitions(openApi);
	}
}
