package org.tnmk.practicespringjpa.hotelview.model.converter;



import org.tnmk.common.jpa.columnconverter.json.JsonConverter;
import org.tnmk.practicespringjpa.hotelview.model.AlternateCode;

import javax.persistence.Converter;
import java.util.List;

@Converter
public class AlternateCodesConverter extends JsonConverter<List<AlternateCode>> {

}
