package com.metaway.petshop.configurations.myBatis.typeHandler;

import com.metaway.petshop.enums.ContactType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactTypeHandler extends BaseTypeHandler<ContactType> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, ContactType parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public ContactType getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String contactType = rs.getString(columnName);
        return contactType != null ? ContactType.valueOf(contactType) : null;
    }

    @Override
    public ContactType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String contactType = rs.getString(columnIndex);
        return contactType != null ? ContactType.valueOf(contactType) : null;
    }

    @Override
    public ContactType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String contactType = cs.getString(columnIndex);
        return contactType != null ? ContactType.valueOf(contactType) : null;
    }
}
