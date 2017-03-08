# Integration of Applications

## Description 

This repository contains work on Java plateform (Java EE)

The goal of the main project is to provide a web service (JAX-RS) that propose features to manage a doctor office.

## Features

- Handle a calendar for doctors
  - Add time slots for doctors
  - Consult available time slots
- Handle patients appointments
  - Make an appointment
  - Update an appointment
  - Cancel an appointment

## Technologies

This project is based on Java EE and use features provided by an application server like the possibility to use a persistence context (JTA).
For this project we use Payara server to host our application.

There are various implementations of EJBs to properly handle business logic.

As said before, we use JAX-RS API to provide a web service as an interface for our project.

## Structure

The main project is in the project folder where there is 2 sub directories.
- appJEE: Contains project sources
- deployement: Contains a script to fully deploy the given artifact on a Payara server (Prepare environnement + Asadmin commands).
