package com.hamza.student_management_system.core.utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamza.student_management_system.course.entities.Course;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class PdfGenerator {

    public void generateCourseSchedulePdf(HttpServletResponse response, Course course) throws IOException {

        Document pdfDocument = this.preparePdfDocument(response, course);

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=course_schedule_" + course.getCourseCode() + ".pdf";
        response.addHeader(headerKey, headerValue);
    }

    @Cacheable(value = "schedulePdf", key = "#id")
    private Document preparePdfDocument(HttpServletResponse response, Course course) throws IOException {

        log.info(String.format("Preparing schedule pdf document for course with id %s", course.getId()));
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        //TITLE
        this.createNewParagraph(document, course.getCourseName(),
                getFont(FontFactory.HELVETICA_BOLD, 24),
                Paragraph.ALIGN_CENTER, true);

        //COURSE INFO
        this.createNewParagraph(document, String.format("Course Code: %s", course.getCourseCode()),
                getFont(FontFactory.HELVETICA, 18),
                Paragraph.ALIGN_LEFT, false);

        this.createNewParagraph(document, String.format("Course Credit: %s", course.getCredits()),
                getFont(FontFactory.HELVETICA, 18),
                Paragraph.ALIGN_LEFT, true);

        //SCHEDULE
        this.createNewParagraph(document, "Course Schedule:",
                getFont(FontFactory.HELVETICA, 18),
                Paragraph.ALIGN_LEFT, false);

        HashMap<String, String> resultScheduleMap = this.convertJsonStringToHashMap(course.getSchedule());
        for (Map.Entry<String, String> entry : resultScheduleMap.entrySet()) {
            this.createNewParagraph(document, String.format("Day: %s, Time: %s.", entry.getKey(), entry.getValue()),
                    getFont(FontFactory.HELVETICA_OBLIQUE, 14),
                    Paragraph.ALIGN_LEFT, false);
        }

        document.close();
        return document;
    }

    private void createNewParagraph(Document document, String paragraphContent, Font paragraphFont, int paragraphAlignment, boolean setSpace) {
        Paragraph paragraph = new Paragraph(paragraphContent, paragraphFont);
        paragraph.setAlignment(paragraphAlignment);

        if (setSpace) {
            paragraph.setSpacingAfter(2f);
        }

        document.add(paragraph);
    }

    private Font getFont(String fontName, int fontSize) {
        Font font = FontFactory.getFont(fontName);
        font.setSize(fontSize);
        return font;
    }

    private HashMap<String, String> convertJsonStringToHashMap(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
        return gson.fromJson(jsonString, type);
    }
}
