package io.wulfcodes.library.mapper;

import java.time.LocalDate;
import java.util.List;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import io.wulfcodes.library.model.dto.BookData;
import io.wulfcodes.library.model.dto.LoanData;
import io.wulfcodes.library.model.dto.UserData;
import io.wulfcodes.library.model.po.Loan;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface LoanMapper {

    @Mapping(target = "loanId", source = "id")
    @Mapping(target = "status", source = "dueDate", qualifiedByName = "mapStatus")
    LoanData toData(Loan loan);

    @InheritConfiguration(name = "toData")
    @Mapping(target = "loanId", source = "loan.id")
    @Mapping(target = "status", source = "loan.dueDate", qualifiedByName = "mapStatus")
    @Mapping(target = "userId", source = "userData.userId")
    @Mapping(target = "email", source = "userData.email")
    @Mapping(target = "fullName", source = "userData.fullName")
    @Mapping(target = "bookId", source = "bookData.bookId")
    @Mapping(target = "title", source = "bookData.title")
    @Mapping(target = "author", source = "bookData.author")
    @Mapping(target = "category", source = "bookData.category")
    LoanData toData(Loan loan, UserData userData, BookData bookData);

    List<LoanData> toDataList(Iterable<Loan> loans);

    @Named("mapStatus")
    default String mapStatus(LocalDate dueDate) {
        LocalDate today = LocalDate.now();

        if (dueDate.isBefore(today)) {
            return "overdue";
        } else if (dueDate.isEqual(today)) {
            return "lastday";
        } else {
            return "ok";
        }
    }
}
