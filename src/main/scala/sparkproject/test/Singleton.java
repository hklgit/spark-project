package sparkproject.test;

/**
 * 单例模式Demo
 * 
 * 单例模式是指的什么意思？
 * 
 * 我们自己定义的类，其实默认情况下，都是可以让外界的代码随意创建任意多个实例的
 * 但是有些时候，我们不希望外界来随意创建实例，而只是希望一个类，在整个程序运行期间，只有一个实例
 * 任何外界代码，都不能随意创建实例
 * 
 * 那么，要实现单例模式，有几个要点：
 * 1、如果不想让外界可以随意创建实例，那么类的构造方法就必须用private修饰，必须是私有的
 * 2、既然类的构造方法被私有化了，外界代码要想获取类的实例，不能够随意地去创建
 * 那么就只能通过调用类的静态方法，去获取类的实例
 * 3、所以类必须有一个静态方法，getInstance()，来提供获取唯一实例的功能
 * getInstance()方法，必须保证类的实例创建，且仅创建一次，返回一个唯一的实例
 * 
 * 单例模式的应用场景有哪几个呢？
 * 1、配置管理组件，可以在读取大量的配置信息之后，用单例模式的方式，就将配置信息仅仅保存在一个实例的
 * 实例变量中，这样可以避免对于静态不变的配置信息，反复多次的读取
 * 2、JDBC辅助组件，全局就只有一个实例，实例中持有了一个内部的简单数据源
 * 使用了单例模式之后，就保证只有一个实例，那么数据源也只有一个，不会重复创建多次数据源（数据库连接池）
 * 
 * @author Administrator
 *
 */
public class Singleton {

	// 首先必须有一个私有的静态变量，来引用自己即将被创建出来的单例
	private static com.ibeifeng.sparkproject.test.Singleton instance = null;
	
	/**
	 * 其次，必须对自己的构造方法使用private进行私有化
	 * 这样，才能保证，外界的代码不能随意的创建类的实例
	 */
	private Singleton() {
		
	}
	
	/**
	 * 最后，需要有一个共有的，静态方法
	 * 这个方法，负责创建唯一的实例，并且返回这个唯一的实例
	 * 
	 * 必须考虑到可能会出现的多线程并发访问安全的问题
	 * 就是说，可能会有多个线程同时过来获取单例，那么可能会导致创建多次单例
	 * 所以，这个方法，通常需要进行多线程并发访问安全的控制
	 * 
	 * 首先，就是，说到多线程并发访问安全的控制，大家觉得最简单的就是在方法上加入synchronized关键词
	 * public static synchronized Singleton getInstance()方法
	 * 但是这样做有一个很大的问题
	 * 在第一次调用的时候，的确是可以做到避免多个线程并发访问创建多个实例的问题
	 * 但是在第一次创建完实例以后，就会出现以后的多个线程并发访问这个方法的时候，就会在方法级别进行同步
	 * 导致并发性能大幅度降低
	 * 
	 * @return
	 */
	public static com.ibeifeng.sparkproject.test.Singleton getInstance() {
		// 两步检查机制
		// 首先第一步，多个线程过来的时候，判断instance是否为null
		// 如果为null再往下走
		if(instance == null) {
			// 在这里，进行多个线程的同步
			// 同一时间，只能有一个线程获取到Singleton Class对象的锁
			// 进入后续的代码
			// 其他线程，都是只能够在原地等待，获取锁
			synchronized(com.ibeifeng.sparkproject.test.Singleton.class) {
				// 只有第一个获取到锁的线程，进入到这里，会发现是instance是null
				// 然后才会去创建这个单例
				// 此后，线程，哪怕是走到了这一步，也会发现instance已经不是null了
				// 就不会反复创建一个单例
				if(instance == null) {
					instance = new com.ibeifeng.sparkproject.test.Singleton();
				}
			}
		}
		return instance;
	}
	
}
