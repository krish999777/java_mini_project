import data.*;
import myExceptions.*;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.*;
import org.apache.pdfbox.pdmodel.font.*;

public class ResumeGenerator {
    public static void generate() throws RoleException,SQLException,GeneralException,IOException{
        StudentProfileModel p=StudentProfile.getProfile();
        String filename="resume.pdf";
        if(p==null){
            throw new GeneralException("Student profile does not exist");
        }
        PDDocument doc=new PDDocument();
        PDPage page=new PDPage(PDRectangle.A4);
        doc.addPage(page);
        PDPageContentStream cs=new PDPageContentStream(doc,page);
        try{
            PDFont fontBold=new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            PDFont fontRegular=new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            float x=45f;
            float y=800f;
            float width=505f;

            drawText(cs,fontBold,18,p.fullName,x,y);
            y-=14;

            String contact=p.email;
            if(p.phone!=null && p.phone.length()!=0){
                contact+=" | "+p.phone;
            }
            if(p.college!=null && p.college.length()!=0){
                contact+=" | "+p.college;
            }
            if(p.course!=null && p.course.length()!=0){
                contact+=" ("+p.course+")";
            }
            drawText(cs,fontRegular,9,contact,x,y);
            y-=12;

            String links="";
            if(p.githubUrl!=null && p.githubUrl.length()!=0){
                links+=p.githubUrl;
            }
            if(p.linkedinUrl!=null && p.linkedinUrl.length()!=0){
                links+=(links.length()==0?"":" | ")+p.linkedinUrl;
            }
            if(links.length()!=0){
                drawText(cs,fontRegular,9,links,x,y);
                y-=12;
            }

            if(p.bio!=null && p.bio.length()!=0){
                y=drawSectionHeader(cs,fontBold,"SUMMARY",x,y,width);
                y=drawWrappedText(cs,fontRegular,9,p.bio,x,y,width,11);
            }

            if(p.education!=null && p.education.length!=0){
                y=drawSectionHeader(cs,fontBold,"EDUCATION",x,y,width);
                for(int i=0;i<p.education.length;i++){
                    EducationModel ed=p.education[i];
                    String line=ed.institution;
                    if(ed.degree!=null && ed.degree.length()!=0){
                        line+=" - "+ed.degree;
                    }
                    String years="";
                    if(ed.startYear!=-1 || ed.endYear!=-1){
                        years=(ed.startYear==-1?"":""+ed.startYear)+" - "+(ed.endYear==-1?"":""+ed.endYear);
                    }
                    if(years.length()!=0){
                        line+=" ("+years+")";
                    }
                    if(ed.grade!=null && ed.grade.length()!=0){
                        line+=" | Grade: "+ed.grade;
                    }
                    drawText(cs,fontBold,9,line,x,y);
                    y-=12;
                }
            }

            if(p.internships!=null && p.internships.length!=0){
                y=drawSectionHeader(cs,fontBold,"EXPERIENCE",x,y,width);
                for(int i=0;i<p.internships.length;i++){
                    InternshipModel in=p.internships[i];
                    String heading=in.company;
                    if(in.role!=null && in.role.length()!=0){
                        heading+=" - "+in.role;
                    }
                    String dates="";
                    if(in.startDate!=null && in.startDate.length()!=0){
                        dates+=in.startDate;
                    }
                    if(in.endDate!=null && in.endDate.length()!=0){
                        dates+=" to "+in.endDate;
                    }
                    if(dates.length()!=0){
                        heading+=" ("+dates+")";
                    }
                    drawText(cs,fontBold,9,heading,x,y);
                    y-=12;
                    if(in.description!=null && in.description.length()!=0){
                        y=drawWrappedText(cs,fontRegular,9,in.description,x+10,y,width-10,11);
                    }
                    y-=2;
                }
            }

            if(p.projects!=null && p.projects.length!=0){
                y=drawSectionHeader(cs,fontBold,"PROJECTS",x,y,width);
                for(int i=0;i<p.projects.length;i++){
                    ProjectModel pr=p.projects[i];
                    String heading=pr.title;
                    String prLinks="";
                    if(pr.githubUrl!=null && pr.githubUrl.length()!=0){
                        prLinks+=pr.githubUrl;
                    }
                    if(pr.liveUrl!=null && pr.liveUrl.length()!=0){
                        prLinks+=(prLinks.length()==0?"":" | ")+pr.liveUrl;
                    }
                    if(prLinks.length()!=0){
                        heading+=" ("+prLinks+")";
                    }
                    drawText(cs,fontBold,9,heading,x,y);
                    y-=12;
                    if(pr.description!=null && pr.description.length()!=0){
                        y=drawWrappedText(cs,fontRegular,9,pr.description,x+10,y,width-10,11);
                    }
                    y-=2;
                }
            }

            if(p.skills!=null && p.skills.length!=0){
                y=drawSectionHeader(cs,fontBold,"SKILLS",x,y,width);
                String skillsList=String.join(", ",p.skills);
                y=drawWrappedText(cs,fontRegular,9,skillsList,x,y,width,12);
            }

            if(p.achievements!=null && p.achievements.length!=0){
                y=drawSectionHeader(cs,fontBold,"ACHIEVEMENTS",x,y,width);
                for(int i=0;i<p.achievements.length;i++){
                    AchievementModel ac=p.achievements[i];
                    String line="- "+ac.title;
                    if(ac.description!=null && ac.description.length()!=0){
                        line+=": "+ac.description;
                    }
                    y=drawWrappedText(cs,fontRegular,9,line,x,y,width,11);
                }
            }

            if(p.certifications!=null && p.certifications.length!=0){
                y=drawSectionHeader(cs,fontBold,"CERTIFICATIONS",x,y,width);
                for(int i=0;i<p.certifications.length;i++){
                    CertificationModel cr=p.certifications[i];
                    String line="- "+cr.name;
                    if(cr.issuingOrganization!=null && cr.issuingOrganization.length()!=0){
                        line+=" ("+cr.issuingOrganization+")";
                    }
                    if(cr.credentialId!=null && cr.credentialId.length()!=0){
                        line+=" [ID: "+cr.credentialId+"]";
                    }
                    if(cr.credentialUrl!=null && cr.credentialUrl.length()!=0){
                        line+=" - "+cr.credentialUrl;
                    }
                    y=drawWrappedText(cs,fontRegular,9,line,x,y,width,11);
                }
            }
        }finally{
            cs.close();
        }
        doc.save(filename);
        doc.close();
    }


    private static float drawSectionHeader(PDPageContentStream cs,PDFont font,String title,float x,float y,float width) throws IOException{
        y-=10;
        drawText(cs,font,11,title,x,y);
        y-=4;
        cs.moveTo(x,y);
        cs.lineTo(x+width,y);
        cs.setLineWidth(0.75f);
        cs.stroke();
        y-=10;
        return y;
    }


    private static void drawText(PDPageContentStream cs,PDFont font,float size,String text,float x,float y) throws IOException{
        text=clean(text);
        if(text.length()==0){
            return;
        }
        cs.beginText();
        cs.setFont(font,size);
        cs.newLineAtOffset(x,y);
        cs.showText(text);
        cs.endText();
    }


    private static float drawWrappedText(PDPageContentStream cs,PDFont font,float size,String text,float x,float y,float maxWidth,float leading) throws IOException{
        text=clean(text);
        if(text.length()==0){
            return y;
        }
        String words[]=text.split("\\s+");
        String line="";
        for(int i=0;i<words.length;i++){
            String testLine=line.length()==0?words[i]:line+" "+words[i];
            float lineWidth=font.getStringWidth(testLine)/1000*size;
            if(lineWidth>maxWidth && line.length()!=0){
                drawText(cs,font,size,line,x,y);
                y-=leading;
                line=words[i];
            }else{
                line=testLine;
            }
        }
        if(line.length()!=0){
            drawText(cs,font,size,line,x,y);
            y-=leading;
        }
        return y;
    }


    private static String clean(String s){
        if(s==null){
            return "";
        }else{
            return s.trim();
        }
    }
}
