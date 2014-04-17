package com.cmw.core.kit.email;

import javax.mail.internet.InternetAddress;

@SuppressWarnings("serial")
public class InternetAddressExtend extends InternetAddress {

	public String getEncodedPersonal(){
		return this.encodedPersonal;
	}
}
