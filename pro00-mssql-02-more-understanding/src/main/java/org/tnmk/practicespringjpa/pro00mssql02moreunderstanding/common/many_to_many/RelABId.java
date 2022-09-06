package org.tnmk.practicespringjpa.pro00mssql02moreunderstanding.common.many_to_many;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * https://www.jpa-buddy.com/blog/the-ultimate-guide-on-composite-ids-in-jpa-entities/
 * The composite key must have equals(), hashCode(), and Serializable.
 */
@Getter
@Setter
@EqualsAndHashCode
public class RelABId implements Serializable {

  private int aId;
  private int bId;
}
