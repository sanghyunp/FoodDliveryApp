package com.cogent.fooddeliveryapp.utils;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomNamingStrategy extends PhysicalNamingStrategyStandardImpl {

	@Override // to modify table name, Identifier table detail, JdbcEnvironment getting database infomation
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		// TODO Auto-generated method stub
		String newName = name.getText().concat("_TBL");
		return Identifier.toIdentifier(newName); // transform toIdentifier
	}

	
}
