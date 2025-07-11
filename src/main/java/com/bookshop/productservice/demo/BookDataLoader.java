package com.bookshop.productservice.demo;


import com.bookshop.productservice.domain.Book;
import com.bookshop.productservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("testdata") //해당 프로파일이 활성화 된 경우에만 클래스를 로드함
public class BookDataLoader {

	private final BookRepository bookRepository;

	public BookDataLoader(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@EventListener(ApplicationReadyEvent.class)//해당 이벤트가 발생하면 테스트 데이터 생성이 시작됨 애플리 케이션 시작 단계가 완료되면 발생
	public void loadBookTestData() {
		bookRepository.deleteAll(); //비어있는 데이터 베이스로 시작
		var book1 = Book.of("1234567891", "Northern Lights", "Lyra Silverstar", 9.90, "Polarsophia");
		var book2 = Book.of("1234567892", "Polar Journey", "Iorek Polarson", 12.90, "Polarsophia");
		bookRepository.saveAll(List.of(book1,book2));
	}

}
