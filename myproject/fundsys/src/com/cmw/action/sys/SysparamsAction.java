/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2010, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.ejb.criteria.expression;

import java.io.Serializable;
import java.util.Map;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.MapAttribute;

import org.hibernate.ejb.criteria.CriteriaBuilderImpl;
import org.hibernate.ejb.criteria.CriteriaQueryCompiler;
import org.hibernate.ejb.criteria.ParameterRegistry;
import org.hibernate.ejb.criteria.PathImplementor;
import org.hibernate.ejb.criteria.Renderable;

/**
 * TODO : javadoc
 *
 * @author Steve Ebersole
 */
public class MapEntryExpression<K,V>
		extends ExpressionImpl<Map.Entry<K,V>>
		implements Expression<Map.Entry<K,V>>, Serializable {

	private final PathImplementor origin;
	private final MapAttribute<?, K, V> attribute;

	public MapEntryExpression(
			CriteriaBuilderImpl criteriaBuilder,
			Class<Map.Entry<K, V>> javaType,
			PathImplementor origin,
			MapAttribute<?, K, V> attribute) {
		super( criteriaBuilder, javaType);
		this.origin = origin;
		this.attribute = attribute;
	}

	public MapAttribute<?, K, V> getAttribute() {
		return attribute;
	}

	public void registerParameters(ParameterRegistry registry) {
		// none to register
	}

	public String render(CriteriaQueryCompiler.RenderingContext renderingContext) {
		// don't think this is valid outside of select clause...
		throw new IllegalStateException( "illegal reference to map entry outside of select clause." );
	}

	public String renderProjection(CriteriaQueryCompiler.RenderingContext renderingContext) {
		return "entry(" + path( renderingContext ) + ")";
	}

	private String path(CriteriaQueryCompiler.RenderingContext renderingContext) {
		return origin.getPathIdentifier()
				+ '.'
				+ ( (Renderable) getAttribute() ).renderProjection( renderingContext );
	}
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    /*
 * Copyright 2002-2003,2009 The Apache Software Foundation.
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
package com.opensymphony.xwork2.util;

import java.util.List;


/**
 * @author <a href="mailto:plightbo@cisco.com">Pat Lightbody</a>
 * @author $Author: martinc $
 * @version $Revision: 894090 $
 */
public class Cat {

    public static final String SCIENTIFIC_NAME = "Feline";

    Foo foo;
    List kittens;
    String name;


    public void setFoo(Foo foo) {
        this.foo = foo;
    }

    public Foo getFoo() {
        return foo;
    }

    public void setKittens(List kittens) {
        this.kittens = kittens;
    }

    public List getKittens() {
        return kittens;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 