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
import javax.persistence.criteria.Expression;
import org.hibernate.ejb.criteria.ParameterRegistry;
import org.hibernate.ejb.criteria.CriteriaBuilderImpl;
import org.hibernate.ejb.criteria.CriteriaQueryCompiler;
import org.hibernate.ejb.criteria.Renderable;

/**
 * Models unary arithmetic operation (unary plus and unary minus).
 *
 * @author Steve Ebersole
 */
public class UnaryArithmeticOperation<T> 
		extends ExpressionImpl<T>
		implements UnaryOperatorExpression<T>, Serializable {

	public static enum Operation {
		UNARY_PLUS, UNARY_MINUS
	}

	private final Operation operation;
	private final Expression<T> operand;

	@SuppressWarnings({ "unchecked" })
	public UnaryArithmeticOperation(
			CriteriaBuilderImpl criteriaBuilder,
			Operation operation,
			Expression<T> operand) {
		super( criteriaBuilder, (Class)operand.getJavaType() );
		this.operation = operation;
		this.operand = operand;
	}

	public Operation getOperation() {
		return operation;
	}

	/**
	 * {@inheritDoc}
	 */
	public Expression<T> getOperand() {
		return operand;
	}

	/**
	 * {@inheritDoc}
	 */
	public void registerParameters(ParameterRegistry registry) {
		Helper.possibleParameter( getOperand(), registry );
	}

	public String render(CriteriaQueryCompiler.RenderingContext renderingContext) {
		return ( getOperation() == Operation.UNARY_MINUS ? '-' : '+' )
				+ ( (Renderable) getOperand() ).render( renderingContext );
	}

	public String renderProjection(CriteriaQueryCompiler.RenderingContext renderingContext) {
		return render( renderingContext );
	}
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         /*
 * Copyright 2005 The Apache Software Foundation.
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
package com.opensymphony.xwork2.util.location;

import com.opensymphony.xwork2.util.ClassLoaderUtil;
import junit.framework.TestCase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.List;

public class LocationImplTest extends TestCase {
    
    public LocationImplTest(String name) {
        super(name);
    }
    
    static final String str = "path/to/file.xml:1:40";

    public void testEquals() throws Exception {
        Location loc1 = LocationUtils.parse(str);
        Location loc2 = new LocationImpl(null, "path/to/file.xml", 1, 40);
        
        assertEquals("locations", loc1, loc2);
        assertEquals("hashcode", loc1.hashCode(), loc2.hashCode());
        assertEquals("string representation", loc1.toString(), loc2.toString());
    }
    
    /**
     * Test that Location.UNKNOWN is kept identical on deserialization
     */
    public void testSerializeUnknown() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new Object