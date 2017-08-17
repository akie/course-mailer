package jp.co.yahoo.mailsender.service;

import jp.co.yahoo.mailsender.data.AddressBook;
import jp.co.yahoo.mailsender.data.AddressItem;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.StyledEditorKit;
import java.io.File;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressBookServiceImplTest {

    @Autowired
    private AddressBookService addressBookService;

    @Before
    public void initFile(){
        File file = new File(AddressBook.FILE_PATH);
        boolean isDelete = file.delete();
        System.out.println("file delete result is " + isDelete);
    }

    @Test
    public void addMailAddress() throws Exception {
        addressBookService.add(new AddressItem("xxx@gmail.com"));
    }
    @Test(expected = Exception.class)
    public void mailAddressEmpty() throws Exception {
        addressBookService.add(new AddressItem(""));
    }

    @Test
    public void getMailAddress() throws Exception {
        String mailAddress = "yyy@gmail.com";
        addressBookService.add(new AddressItem(mailAddress));
        List<AddressItem> addressItems = addressBookService.get();
        assertThat(addressItems.size(), is(1));

        Boolean isContains = false;

        for (AddressItem item : addressItems) {
            if (item.getMailAddress().equals(mailAddress)) {
                isContains = true;
            }
        }
        assertThat(isContains, is(true));
    }

    @Test
    public void getWhenNoItem() {
        List<AddressItem> addressItems = addressBookService.get();
        assertThat(addressItems.size(), is(0));
    }
}