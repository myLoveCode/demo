package com.example.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Demo;

public interface DemoDao extends JpaRepository<Demo, Long> {
	List<Demo> findByAddress(String name);

	Demo findByNameAndAddress(String name, String address);

	@Query("select p from Person p where p.name=:name and p.address=:address")
	Demo withNameAndAddressQuery(@Param("name") String name, @Param("address") String address);

	@Query(value = "select * from person p where p.name=:name and p.address=:address", nativeQuery = true)
	Demo withNameAndAddressNamedQuery(@Param("name") String name, @Param("address") String address);
}
