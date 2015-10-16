package com.farm.web.test;

import com.farm.console.prisist.domain.AloneOrganization;
import com.farm.console.prisist.domain.AloneUser;

public class FarmTest {
	public AloneUser user = new AloneUser("noUser", "pw", "201312121212",
			"201312121212", "noneId", "noneId", "1", "noUser");
	public AloneOrganization org = new AloneOrganization("treecode", "name",
			"ctime", "utime", "state", "cuser", "muser", "type");
}
