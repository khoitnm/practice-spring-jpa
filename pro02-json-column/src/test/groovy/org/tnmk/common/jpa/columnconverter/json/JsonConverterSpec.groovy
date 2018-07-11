package org.tnmk.common.jpa.columnconverter.json

import spock.lang.Specification

class JsonConverterSpec extends Specification{

    def 'create jsonConverter with ParameterizedType successfully'(){
        given:
        JsonConverter jsonTypeConverter = new ParameterizedTypeJsonConverter()
        Map<String, SimpleModel> originalData = new HashMap<>();
        originalData.put("simpleModelA", constructDefault())
        originalData.put("simpleModelB", constructDefault())

        when:
        String json = jsonTypeConverter.convertToDatabaseColumn(originalData);
        Map<String, SimpleModel> convertedData = jsonTypeConverter.convertToEntityAttribute(json);

        then:
        convertedData.get("simpleModelA").getId() == originalData.get("simpleModelA").getId()
    }

    def 'create jsonConverter with Class successfully'(){
        given:
        JsonConverter jsonTypeConverter = new SimpleModelJsonConverter()
        SimpleModel originalData = constructDefault();
        when:
        String json = jsonTypeConverter.convertToDatabaseColumn(originalData);
        SimpleModel convertedData = jsonTypeConverter.convertToEntityAttribute(json);

        then:
        convertedData.getId() == originalData.getId()
    }

    private SimpleModel constructDefault(){
        SimpleModel simpleModel = new SimpleModel();
        simpleModel.setId(System.nanoTime());
        simpleModel.setName("Name_"+System.nanoTime())
        return simpleModel;
    }
}
