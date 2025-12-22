package io.wulfcodes.library.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import io.wulfcodes.library.model.dto.BookData;
import io.wulfcodes.library.model.po.Book;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BookMapper {

    @Mapping(target = "bookId", source = "id")
    BookData toData(Book book);

    List<BookData> toDataList(Iterable<Book> books);

}
