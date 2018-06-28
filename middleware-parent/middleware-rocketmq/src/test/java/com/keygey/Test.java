package com.keygey;

import org.apache.rocketmq.common.message.MessageConst;
import org.apache.rocketmq.common.sysflag.MessageSysFlag;

public class Test {
	public static void main(String[] args) {
		int sysFlag = 0;
		sysFlag |= MessageSysFlag.COMPRESSED_FLAG;

		sysFlag |= MessageSysFlag.TRANSACTION_PREPARED_TYPE;
		System.out.println(MessageSysFlag.COMPRESSED_FLAG);
	}
}
