package com.enter.entity;


import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.util.Date;

@Data
public class Course {
    private int id;
    private String userId;
    private String name;

}
