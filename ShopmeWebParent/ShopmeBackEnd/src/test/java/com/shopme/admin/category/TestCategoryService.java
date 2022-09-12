package com.shopme.admin.category;

import com.shopme.common.entities.Category;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class TestCategoryService {

    @Mock
    private CategoryRepository repository;

    @InjectMocks
    private CategoryService service;

    @Test
    public void testCheckUniqueInNewModeReturnDuplicateName(){
        Integer id = null;
        String name = "Computer";
        String alias = "abc";

        Category category = new Category(id, name, alias);

        Mockito.when(repository.findByName(name)).thenReturn(category);
        Mockito.lenient().when(repository.findByAlias(alias)).thenReturn(null);
        String result = service.checkUnique(id, name, alias);
        assertThat(result).isEqualTo("DuplicateName");
    }

    @Test
    public void testCheckUniqueInNewModeReturnDuplicateAlias(){
        Integer id = null;
        String name = "abc";
        String alias = "computer";

        Category category = new Category(id, name, alias);

        Mockito.when(repository.findByAlias(alias)).thenReturn(category);
        Mockito.lenient().when(repository.findByAlias(name)).thenReturn(null);
        String result = service.checkUnique(id, name, alias);
        assertThat(result).isEqualTo("DuplicateAlias");
    }

    @Test
    public void testCheckUniqueInNewModeReturnOK(){
        Integer id = null;
        String name = "NameABC";
        String alias = "computer";

//        Category category = new Category(id, name, alias);

        Mockito.when(repository.findByName(name)).thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(null);

        String result = service.checkUnique(id, name, alias);
        System.out.println(result);
        assertThat(result).isEqualTo("OK");
    }

    @Test
    public void testCheckUniqueInEditReturnDuplicateName(){
        Integer id = 1;
        String name = "Computer";
        String alias = "abx";

        Category category = new Category(2, name, alias);

        Mockito.when(repository.findByName(name)).thenReturn(category);
        Mockito.lenient().when(repository.findByAlias(alias)).thenReturn(null);
        String result = service.checkUnique(id, name, alias);
        assertThat(result).isEqualTo("DuplicateName");
    }

    @Test
    public void testCheckUniqueInEditModeReturnDuplicateAlias(){
        Integer id = 2;
        String name = "abc";
        String alias = "computer";

        Category category = new Category(2, name, alias);

        Mockito.when(repository.findByAlias(alias)).thenReturn(category);
        Mockito.lenient().when(repository.findByName(name)).thenReturn(null);
        String result = service.checkUnique(id, name, alias);
        assertThat(result).isEqualTo("DuplicateAlias");
    }

    @Test
    public void testCheckUniqueInEditModeReturnOK(){
        Integer id = 1;
        String name = "NameABC";
        String alias = "computer";

        Category category = new Category(id, name, alias);

        Mockito.when(repository.findByName(name)).thenReturn(null);
        Mockito.when(repository.findByAlias(alias)).thenReturn(category);

        String result = service.checkUnique(id, name, alias);
        System.out.println(result);
        assertThat(result).isEqualTo("OK");
    }
}