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

import org.hibernate.ejb.criteria.CriteriaBuilderImpl;
import org.hibernate.ejb.criteria.CriteriaQueryCompiler;
import org.hibernate.ejb.criteria.ParameterRegistry;

/**
 * Used to construct the result of {@link javax.persistence.criteria.Path#type()}
 *
 * @author Steve Ebersole
 */
public class PathTypeExpression<T> extends ExpressionImpl<T> implements Serializable {
	public PathTypeExpression(CriteriaBuilderImpl criteriaBuilder, Class<T> javaType) {
		super( criteriaBuilder, javaType );
	}

	public void registerParameters(ParameterRegistry registry) {
		// nothing to do
	}

	public String render(CriteriaQueryCompiler.RenderingContext renderingContext) {
		// todo : is it valid for this to get rendered into the query itself?
		throw new IllegalArgumentException( "Unexpected call on EntityTypeExpression#render" );
	}

	public String renderProjection(CriteriaQueryCompiler.RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            /*
 * Copyright 2002-2006,2009 The Apache Software Foundation.
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
package com.opensymphony.xwork2.util.profiling;

import junit.framework.TestCase;

/**
 * @author tmjee
 * @version $Date: 2009-12-27 19:18:29 +0100 (Sun, 27 Dec 2009) $ $Id: UtilTimerStackTest.java 894090 2009-12-27 18:18:29Z martinc $
 */
public class UtilTimerStackTest extends TestCase {

    protected String activateProp;
    protected String minTimeProp;


    public void testActivateInactivate() throws Exception {
        UtilTimerStack.setActive(true);
        assertTrue(UtilTimerStack.isActive());
        UtilTimerStack.setActive(false);
        assertFalse(UtilTimerStack.isActive());
    }


    public void testPushPop() throws Exception {
        UtilTimerStack.push("p1");
        Thread.sleep(1050);
        ProfilingTimerBean bean = UtilTimerStack.current.get();
        assertTrue(bean.startTime > 0);
        UtilTimerStack.pop("p1");
        assertTrue(bean.totalTime > 1000);
    }


    public void testProfileCallback() throws Exception {

        MockProfilingBlock<String> block = new MockProfilingBlock<String>() {
            @Override
            public String performProfiling() throws Exception {
                Thread.sleep(1050);
                return "OK";
            }
        };
        String result = UtilTimerStack.profile("p1", block);
        assertEquals(result, "OK");
        assertNotNull(block.getProfilingTimerBean());
        assertTrue(block.getProfilingTimerBean().totalTime >= 1000);

    }


    public void testProfileCallbackThrowsException() throws Exception {
        try {
            UtilTimerStack.profile("p1",
                    new UtilTimerStack.ProfilingBlock<String>() {
                        public String doProfiling() throws Exception {
                            throw new RuntimeException("test");
                        }
                    });
            fail("exception should have been thrown");
        }
        catch (Exception e) {
            assertTrue(true);
        }
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        activateProp = System.get