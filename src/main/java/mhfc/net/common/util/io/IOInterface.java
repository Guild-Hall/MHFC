package mhfc.net.common.util.io;

/**
 * An IO interface is pretty much only a converter from type R(aw) to type M(odel)
 *
 * @author WorldSEnder
 *
 */
public interface IOInterface<R, M> extends ReadInterface<R, M>, WriteInterface<R, M> {}
