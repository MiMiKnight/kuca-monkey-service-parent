package com.github.mimiknight.monkey.common.mybatis.typehandler;

import com.github.mimiknight.kuca.utils.constant.DateTimeFormatStandard;
import com.github.mimiknight.kuca.utils.constant.TimeZoneGMT;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 自定义OffsetDateTime日期类型处理程序
 *
 * @author MiMiKnight victor2015yhm@gmail.com
 * @date 2023-08-06 08:11:23
 * @since 0.0.1-SNAPSHOT
 */
@MappedTypes(value = {OffsetDateTime.class})
public class MyOffsetDateTimeTypeHandler extends BaseTypeHandler<OffsetDateTime> {


    /**
     * 数据库日期格式
     * <p>
     * yyyy-MM-dd HH:mm:ss.SSS          此处时间精确到毫秒，对应数据库 datetime时间精确到3位
     * yyyy-MM-dd HH:mm:ss.SSSSSS       此处时间精确到微秒，对应数据库 datetime时间精确到6位
     * yyyy-MM-dd HH:mm:ss.SSSSSSSSS    此处时间精确到纳秒，对应数据库 datetime时间精确到9位
     */
    private static final String DATE_TIME_FORMAT_PATTERN = DateTimeFormatStandard.STANDARD_3;

    /**
     * 入数据库的日期默认时区
     */
    private static final String DATABASE_TIMEZONE = TimeZoneGMT.GMT;

    @Override
    public void setNonNullParameter(PreparedStatement ps, int index, OffsetDateTime dateTime,
                                    JdbcType jdbcType) throws SQLException {
        String dateTimeStr = offsetDateTime2dateTimeStr(dateTime);
        ps.setString(index, dateTimeStr);
    }

    @Override
    public OffsetDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String dateTimeStr = rs.getString(columnName);
        return dateTimeStr2OffsetDateTime(dateTimeStr);
    }

    @Override
    public OffsetDateTime getNullableResult(ResultSet rs, int index) throws SQLException {
        String dateTimeStr = rs.getString(index);
        return dateTimeStr2OffsetDateTime(dateTimeStr);
    }

    @Override
    public OffsetDateTime getNullableResult(CallableStatement cs, int index) throws SQLException {
        String dateTimeStr = cs.getString(index);
        return dateTimeStr2OffsetDateTime(dateTimeStr);
    }

    /**
     * OffsetDateTime转日期字符串
     *
     * @param dateTime 日期时间
     * @return {@link String}
     */
    private String offsetDateTime2dateTimeStr(OffsetDateTime dateTime) {
        if (null == dateTime) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_PATTERN, Locale.ENGLISH);
        OffsetDateTime utcDateTime = dateTime.withOffsetSameInstant(ZoneOffset.of(DATABASE_TIMEZONE));
        return utcDateTime.format(formatter);
    }

    /**
     * 日期时间字符串转OffsetDateTime
     *
     * @param dateTimeStr 日期时间字符串
     * @return {@link OffsetDateTime}
     */
    private OffsetDateTime dateTimeStr2OffsetDateTime(String dateTimeStr) {
        if (null == dateTimeStr) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_PATTERN, Locale.ENGLISH);
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);
        return OffsetDateTime.of(dateTime, ZoneOffset.of(DATABASE_TIMEZONE));
    }
}
