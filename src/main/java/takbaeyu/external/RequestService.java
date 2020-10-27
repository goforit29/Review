
package takbaeyu.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;


@FeignClient(name="payment", url="${api.url.request}")

public interface RequestService {

    @RequestMapping(method= RequestMethod.POST, path="/requests")
    public void request(@RequestBody Request request);

}