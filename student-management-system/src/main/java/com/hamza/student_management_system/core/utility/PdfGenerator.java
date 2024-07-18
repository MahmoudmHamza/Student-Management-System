package com.hamza.student_management_system.core.utility;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hamza.student_management_system.course.entities.Course;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Component
public class PdfGenerator {

    public void generateCourseSchedulePdf(HttpServletResponse response, Course course) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        //TITLE
        this.createNewParagraph(document, course.getCourseName(),
                getFont(FontFactory.HELVETICA_BOLD, 24),
                Paragraph.ALIGN_CENTER, true);

        //COURSE INFO
        this.createNewParagraph(document, "Course Code: " + course.getCourseCode(),
                getFont(FontFactory.HELVETICA, 18),
                Paragraph.ALIGN_LEFT, false);

        this.createNewParagraph(document, "Course Credit: " + course.getCredits(),
                getFont(FontFactory.HELVETICA, 18),
                Paragraph.ALIGN_LEFT, true);

        //SCHEDULE
        this.createNewParagraph(document, "Course Schedule: ",
                getFont(FontFactory.HELVETICA, 18),
                Paragraph.ALIGN_LEFT, false);

        HashMap<String, String> resultScheduleMap = this.convertJsonStringToHashMap(course.getSchedule());
        for (Map.Entry<String, String> entry : resultScheduleMap.entrySet()) {
            //TODO: use formatted string
            this.createNewParagraph(document, "Day: " + entry.getKey() + ", Time: " + entry.getValue() + ".",
                    getFont(FontFactory.HELVETICA_OBLIQUE, 14),
                    Paragraph.ALIGN_LEFT, false);
        }

        document.close();

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=course_schedule.pdf";
        response.addHeader(headerKey, headerValue);
    }

    private void createNewParagraph(Document document, String paragraphContent, Font paragraphFont, int paragraphAlignment, boolean setSpace) {
        Paragraph paragraph = new Paragraph(paragraphContent, paragraphFont);
        paragraph.setAlignment(paragraphAlignment);

        if(setSpace){
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
        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }
}
