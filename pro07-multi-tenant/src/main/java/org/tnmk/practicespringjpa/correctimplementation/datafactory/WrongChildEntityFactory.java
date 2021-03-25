package org.tnmk.practicespringjpa.correctimplementation.datafactory;

import org.tnmk.practicespringjpa.correctimplementation.entity.WrongChildEntity;

public class WrongChildEntityFactory {

    public static WrongChildEntity constructChildEntity() {
        WrongChildEntity wrongChildEntity = new WrongChildEntity();
        wrongChildEntity.setName("Child_" + System.nanoTime());
        wrongChildEntity.setDescription("Description_" + System.nanoTime());
        return wrongChildEntity;
    }
}
