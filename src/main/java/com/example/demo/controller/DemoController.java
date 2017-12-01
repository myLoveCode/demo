package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dao.DemoDao;
import com.example.demo.entity.Demo;

@RestController
public class DemoController {

	@Resource
	private DemoDao personRepository;

	@RequestMapping("/save")
	public Demo save(String name, String address, Integer age) {
		Demo person = personRepository.save(new Demo(null, name, age, address));
		return person;
	}

	@RequestMapping("/q1")
	public List<Demo> q1(String address) {
		List<Demo> people = personRepository.findByAddress(address);
		return people;
	}

	@RequestMapping("/q2")
	public Demo q2(String name, String address) {
		Demo people = personRepository.findByNameAndAddress(name, address);
		return people;
	}

	@RequestMapping("/q3")
	public Demo q3(String name, String address) {
		Demo person = personRepository.withNameAndAddressQuery(name, address);
		return person;
	}

	@RequestMapping("/q4")
	public Demo q4(String name, String address) {
		Demo person = personRepository.withNameAndAddressNamedQuery(name, address);
		return person;
	}

	@RequestMapping("/sort")
	public List<Demo> sort() {
		System.out.println("第一次修改");
		List<Demo> people = personRepository.findAll(new Sort(Sort.Direction.ASC, "id"));
		return people;
	}

	@RequestMapping("/page")
	public Page<Demo> page(int page, int size) {
		Page<Demo> all = personRepository.findAll(new PageRequest(page, size));
		return all;
	}

	@RequestMapping("/all")
	public List<Demo> all() {
		return personRepository.findAll();
	}

	@RequestMapping(value = "/date", method = RequestMethod.POST)
	public void processDate(@RequestParam("date") LocalDate date) {
		System.out.println(date);
	}

	@RequestMapping(value = "/datetime", method = RequestMethod.POST)
	public void processDateTime(@RequestParam("datetime") LocalDateTime dateAndTime) {
		System.out.println(dateAndTime);
	}

}
