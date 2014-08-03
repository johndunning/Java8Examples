/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package java8examples.lambdas;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

/**
 *
 * @author johndunning
 */
public class Person
{
    private String name;
    private Integer age;

    public Person(String name, int age)
    {
        this.name = name;
        this.age = age;
    }
    
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    @Override
    public String toString()
    {
        return String.format("Person[name=%s, age=%d]",name,age);
    }

    
    //
    
    public static void printCollection(Collection<Person> people)
    {
        System.out.println("People");
        
        for(Person p : people)
        {
            System.out.format(" + %s\n",p.toString());
        }
        
        System.out.println();
    }
    
    public static void sort1(List<Person> people)
    {
        people.sort(new Comparator<Person>()
        {
            @Override
            public int compare(Person p1, Person p2)
            {
                return p1.getAge().compareTo(p2.getAge());
            }
        });
        
        printCollection(people);
    }
    
    public static void sort2(List<Person> people)
    {
        people.sort((Person p1, Person p2) -> {
            return p1.getAge().compareTo(p2.getAge());
        });
        
        printCollection(people);
    }
    
    public static void sort3(List<Person> people)
    {
        people.sort((p1, p2) -> p1.getAge().compareTo(p2.getAge()));
        
        printCollection(people);
    }
    
    public static void main(String[] args)
    {
        Person px1 = new Person("Jon Doe",21);
        Person px2 = new Person("Sally Doe",1);
        Person px3 = new Person("Mark Doe",2);
        Person px4 = new Person("Jane Doe",19);
        Person px5 = new Person("Fred Doe",22);
        Person px6 = new Person("Ian Doe",2);
        
        List<Person> people = new ArrayList<Person>();
        
        people.addAll(Arrays.asList(new Person[]{px1,px2,px3,px4,px5,px6}));
        
        
        printCollection(people);
        
        sort1(people);
        sort2(people);
        sort3(people);
        
        System.out.println("\nSorted By Name");
        people.sort((p1,p2) -> p1.getName().compareTo(p2.getName()));
        people.forEach(p -> System.out.format(" --- %s\n",p));
        
        System.out.println("\nSorted By Age");
        people.sort((p1,p2) -> p1.getAge().compareTo(p2.getAge()));
        people.forEach(p -> System.out.format(" --- %s\n",p));
        
        System.out.println("\nRemove People with Age < 18");
        people.removeIf(p -> p.getAge() < 18);
        people.forEach(p -> System.out.format(" --- %s\n",p));
        
        people.addAll(Arrays.asList(new Person[]{px2,px3,px6}));
        
        System.out.println("\nSorted by Name and Filter Age > 18");
        people.stream()
                .filter(p -> p.getAge() > 18 )
                .sorted((p1,p2) -> p1.getName().compareTo(p2.getName()))
                .forEach(p -> System.out.format(" --- %s\n",p));
        
        System.out.println("\nSorted by Name and Filter Age < 18");
        people.stream()
                .filter(p -> p.getAge() < 18 )
                .sorted((p1,p2) -> p1.getName().compareTo(p2.getName()))
                .forEach(p -> System.out.format(" --- %s\n",p));
        
        System.out.println("\nSorted Names of all People >= 18");
        people.stream()
                .filter(p -> p.getAge() > 18)
                .map(p -> p.getName())          // we now have the name on the stream and not the person
                .sorted()                       // sort the stream (i.e. the name
                .forEach(p -> System.out.format(" --- %s\n",p));
        
        System.out.println("\nNumber of People of each age");
        Map<Integer, Long> countByAge;
        
        countByAge =
            people.stream()
                    .map(p -> p.getAge())
                    .collect(groupingBy(age -> age,counting()));
        
        countByAge.forEach((k,v) -> System.out.format(" --- Age: %d, Count: %d\n",k,v));
        
        System.out.println("\nNumber of People of each age sorted by Count");
        
        countByAge =
            people.stream()
                    .map(p -> p.getAge())
                    .collect(groupingBy(age -> age,counting()));
        
        countByAge
                .entrySet()
                .stream()
                .sorted((e1,e2) -> e2.getValue().compareTo(e1.getValue()))
                .forEach(e -> System.out.format(" --- Count: %d, Age: %d\n",e.getValue(),e.getKey()));
        
        
       System.out.println("Every .foreach in this file");
       
       Pattern pattern = Pattern.compile(".*forEach.*");
       try(BufferedReader br = Files.newBufferedReader(Paths.get("src/java8examples/lambdas/Person.java")))
       {
           br.lines()
                   .map(line -> pattern.matcher(line))
                   .filter(matcher -> matcher.find())
                   .map(matcher -> matcher.group(0).trim())
                   .forEach(s -> System.out.println(s));
       }
       catch(IOException e)
       {
           System.out.println(e.getMessage());
       }
    }
    
}
