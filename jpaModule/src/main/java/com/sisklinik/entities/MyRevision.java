package com.sisklinik.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import com.sisklinik.utility.MyRevisionListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "revinfo")
@RevisionEntity(MyRevisionListener.class)
public class MyRevision implements Serializable {


	private static final long serialVersionUID = -5119823358609823546L;
	
	private int id;
	private long timestamp;
	private Date revisionDate;
	private String userName;
	private boolean current;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="revinfo_sequence")
	@SequenceGenerator(name = "revinfo_sequence", sequenceName = "revinfo_sequence", allocationSize = 1)
	@RevisionNumber
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@RevisionTimestamp
	@Column(name = "timestampDB")
	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Transient
	public Date getRevisionDate() {
		return revisionDate;
	}

	@Transient
	public boolean getCurrent() {
		return current;
	}

	public void setCurrent(boolean current) {
		this.current = current;
	}

	@Column(name = "username")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(current, id, revisionDate, timestamp, userName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MyRevision other = (MyRevision) obj;
		return current == other.current && id == other.id && Objects.equals(revisionDate, other.revisionDate)
				&& timestamp == other.timestamp && Objects.equals(userName, other.userName);
	}
	
	
	
	
}
