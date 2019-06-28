/**
 * This package show the correct way to implement JSON column.
 * <p/>
 * The only different with the code inside {@link org.tnmk.practicespringjpa.wrongimplementation} is the class {@link org.tnmk.practicespringjpa.correctimplementation.entity.ChildEntity} (override equals() and hashCode()) vs. {@link org.tnmk.practicespringjpa.wrongimplementation.entity.WrongChildEntity} (didn't override equals() and hashCode())
 * <p/>
 * Other classes between 2 packages are exactly the same!!!
 */
package org.tnmk.practicespringjpa.correctimplementation;