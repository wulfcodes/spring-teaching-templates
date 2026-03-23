package io.wulfcodes.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import io.wulfcodes.library.model.dto.BookData;
import io.wulfcodes.library.model.po.Book;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {

    Book toEntity(BookData bookData);

    BookData toData(Book book);

}
