package org.tnmk.practicespringjpa.pro06partialupdate.sample.model;

public class PersonProfileUpdate {
  private Long id;

  private String fullName;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
}
