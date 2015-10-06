package ua.pp.fairwind.favorid.internalDB;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

/**
 * Created by Сергей on 06.10.2015.
 * Борьба с ленивой загрузкой в Hibernate при отдаче таких объектов через ВЕБ
 */
public class HibernateAwareObjectMapper extends ObjectMapper {

    public HibernateAwareObjectMapper() {
        registerModule(new Hibernate4Module());
    }
}

