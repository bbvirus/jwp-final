package next.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.QuestionDao;
import next.model.Question;
import core.mvc.Controller;

public class SaveQuestionController implements Controller {
	private QuestionDao questionDao = new QuestionDao();
	private Question question;
	private List<Question> questions;
	
	@Override
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		// 파라미터로 들어온 값들을 파싱
		String writer = (String) request.getParameter("writer");
		String title = (String) request.getParameter("title");
		String contents = (String) request.getParameter("contents");
		
		// Question DTO에 값을 넣어 DB에 Insert
		question = new Question(writer, title, contents);
		questionDao.insert(question);
		
		// 질문 등록이 완료되면
		// 새롭게 등록된 질문을 포함하는 리스트를 보여주는 List 페이지로 이동시키기 위해
		// DB에서 질문들 목록을 읽어오는 부분
		questions = questionDao.findAll();
		request.setAttribute("questions", questions);

		return "list.jsp";
	}
}
