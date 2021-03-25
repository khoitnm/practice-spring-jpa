package org.tnmk.practicespringjpa.pro03jsoncolumn.datafactory;

import org.tnmk.practicespringjpa.pro03jsoncolumn.entity.WrongChildEntity;

public class WrongChildEntityFactory {

    public static WrongChildEntity constructChildEntity() {
        WrongChildEntity wrongChildEntity = new WrongChildEntity();
        wrongChildEntity.setName("Child_" + System.nanoTime());
        wrongChildEntity.setDescription("Description_" + System.nanoTime());
        return wrongChildEntity;
    }
}
