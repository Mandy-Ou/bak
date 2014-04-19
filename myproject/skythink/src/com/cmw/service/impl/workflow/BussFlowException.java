package com.cmw.service.impl.workflow;
/**
 * 业务流程异常
 * @author chengmingwei
 *
 */
@SuppressWarnings("serial")
public class BussFlowException extends Exception {

	public BussFlowException() {
		super();
	}

	public BussFlowException(String message, Throwable cause) {
		super(message, cause);
	}

	public BussFlowException(String message) {
		super(message);
	}

	public BussFlowException(Throwable cause) {
		super(cause);
	}

}
