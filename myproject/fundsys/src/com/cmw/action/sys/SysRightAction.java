/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2009 by Red Hat Inc and/or its affiliates or by
 * third-party contributors as indicated by either @author tags or express
 * copyright attribution statements applied by the authors.  All
 * third-party contributions are distributed under license by Red Hat Inc.
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

import org.hibernate.ejb.criteria.ParameterRegistry;
import org.hibernate.ejb.criteria.CriteriaBuilderImpl;
import org.hibernate.ejb.criteria.CriteriaQueryCompiler;

/**
 * TODO : javadoc
 *
 * @author Steve Ebersole
 */
public class EntityTypeExpression<T> extends ExpressionImpl<T> implements Serializable {
	public EntityTypeExpression(CriteriaBuilderImpl criteriaBuilder, Class<T> javaType) {
		super( criteriaBuilder, javaType );
	}

	public void registerParameters(ParameterRegistry registry) {
		// nothign to do
	}

	public String render(CriteriaQueryCompiler.RenderingContext renderingContext) {
		// todo : is it valid for this to get rendered into the query itself?
		throw new IllegalArgumentException( "Unexpected call on EntityTypeExpression#render" );
	}

	public String renderProjection(CriteriaQueryCompiler.RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           /*
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

import junit.framework.TestCase;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Enumeration;

public class ClassLoaderUtilTest extends TestCase {

    public void testGetResources() throws IOException {
        Iterator<URL> i = ClassLoaderUtil.getResources("xwork-sample.xml", ClassLoaderUtilTest.class, false);
        assertNotNull(i);
        
        assertTrue(i.hasNext());
        URL url = i.next();
        assertTrue(url.toString().endsWith("xwork-sample.xml"));
        assertTrue(!i.hasNext());
    }
    
    public void testGetResources_Multiple() throws IOException {
        Iterator<URL> i = ClassLoaderUtil.getResources("xwork-1.0.dtd", ClassLoaderUtilTest.class, false);
        assertNotNull(i);
        
        assertTrue(i.hasNext());
        URL url = i.next();
        assertTrue(url.toString().endsWith("xwork-1.0.dtd"));
        url = i.next();
        assertTrue(url.toString().endsWith("xwork-1.0.dtd"));
        assertTrue(!i.hasNext());
    }

    public void testGetResources_Aggregate() throws IOException {
        Iterator<URL> i = ClassLoaderUtil.getResources("xwork-1.0.dtd", ClassLoaderUtilTest.class, true);
        assertNotNull(i);

        assertTrue(i.hasNext());
        URL url = i.next();
        assertTrue(url.toString().endsWith("xwork-1.0.dtd"));
        url = i.next();
        assertTrue(url.toString().endsWith("xwork-1.0.dtd"));
        assertTrue(!i.hasNext());
    }

    public void testGetResources_None() throws IOException {
        Iterator<URL> i = ClassLoaderUtil.getResources("asdfasdf.html", ClassLoaderUtilTest.class, false);
        assertNotNull(i);
        
        assertTrue(!i.hasNext());
    }

    public void testGetResource() {
        URL url = ClassLoaderUtil.getResource("xwork-sample.xml", ClassLoaderUtilTest.class);
        assertNotNull(url);
        
        assertTrue(url.toString().endsWith("xwork-sample.xml"));
    }
    
    public void testGetResource_None() {
        URL url = ClassLoaderUtil.getResource("asf.xml", ClassLoaderUtilTest.class);
        assertNull(url);
    }

    public void testAggregateIterator() {
       ClassLoaderUtil.AggregateIterator<String> aggr = new ClassLoaderUtil.AggregateIterator<String>();

       Enumeration en1 = new Enumeration() {
           private Iterator itt = Arrays.asList("str1", "str1", "str3", "str1").it