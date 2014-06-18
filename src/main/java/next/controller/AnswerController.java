package next.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import com.google.gson.Gson;

import core.mvc.Controller;

public class AnswerController implements Controller {
	private AnswerDao answerDao = new AnswerDao();
	private Answer answer;
	private QuestionDao questionDao = new QuestionDao();
	private Question question;
	
	@Override
	public String execute(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		// ajax통신용 응답을 위한 세팅
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		
		// request로 들어온 answer 내용 파싱
		String writer = request.getParameter("writer");
		String contents = request.getParameter("contents");
		long questionId = Long.parseLong(request.getParameter("questionId"));
		
		// answer내용을 DB에 저장
		answer = new Answer(writer, contents, questionId);
		answerDao.insert(answer);
		
		// question테이블의 countOfComment 업데이트
		question = questionDao.findById(questionId);
		questionDao.updateCountOfComment(question);
		
		out.println(gson.toJson(answer));
		return "api";
	}
}
