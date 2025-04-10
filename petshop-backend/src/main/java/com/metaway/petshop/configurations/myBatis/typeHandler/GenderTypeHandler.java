package com.metaway.petshop.configurations.myBatis.typeHandler;

import com.metaway.petshop.enums.Gender;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenderTypeHandler extends BaseTypeHandler<Gender> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Gender parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public Gender getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String gender = rs.getString(columnName);
        return gender != null ? Gender.valueOf(gender) : null;
    }

    @Override
    public Gender getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String gender = rs.getString(columnIndex);
        return gender != null ? Gender.valueOf(gender) : null;
    }

    @Override
    public Gender getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String gender = cs.getString(columnIndex);
        return gender != null ? Gender.valueOf(gender) : null;
    }
}
