package priv.maanjin.shirojwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import priv.maanjin.shirojwt.util.RedisLock;

@Component
public class Service {
	
	@Autowired
	private RedisLock redisLock;
	
	int n = 500;

    public void seckill() {
        // 返回锁的value值，供释放锁时候进行判断
    	while(true) {
    		if (redisLock.lock("wms-count", Thread.currentThread().getName())) {
    			break;
    		}
    		
    		try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	
        System.out.println(Thread.currentThread().getName() + "获得了锁");
        System.out.println(--n);
        redisLock.lock("wms-count", Thread.currentThread().getName());
    }

}
