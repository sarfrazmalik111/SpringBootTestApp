package com.test.web;

import com.test.modalDT.Course;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping({ "/courses" })
public class TestController {

    static List<Course> courses = new ArrayList<>();

    @GetMapping("")
    public List<Course> getCourses() {
        System.out.println("---------------getCourses-------------"+courses.size());
        return courses;
    }

    @PostMapping("/add")
    public Boolean addCourse(@RequestBody Course course) {
        System.out.println("---------------addCourse-------------");
        courses.add(course);
        System.out.println(courses.size());
        return true;
    }

    @PostMapping("/update")
    public Boolean updateCourse(@RequestBody Course course){
        System.out.println("---------------updateCourse-------------");
        Course object = getCourseByID(course.getId());
        object.setTitle(course.getTitle());
        object.setDescription(course.getDescription());
        return true;
    }

    @GetMapping("/delete/{id}")
    public Boolean deleteCourse(@PathVariable Integer id){
        System.out.println("---------------deleteCourse-------------"+id);
        Course course = getCourseByID(id);
        return courses.remove(course);
    }

    private Course getCourseByID(Integer id){
        Course object = null;
        for (Course course: courses){
            if(course.getId().equals(id)){
                object = course;
                break;
            }
        }
        return object;
    }
    private Integer getCourseIndexByID(Integer id){
        Integer index = null;
        for (Course course: courses){
            if(course.getId().equals(id)){
                index = courses.indexOf(course);
                break;
            }
        }
        return index;
    }

    @GetMapping("/get-digi-locker-data")
    public String getDigiLockerData(@RequestBody String data){
        System.out.println("---------------getDigiLockerData-------------");
        System.out.println(data);
        return data;
    }

}
