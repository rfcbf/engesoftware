package br.com.renato.exception;

public class NotFoundException extends Exception {

    private static final long serialVersionUID = -1919724862074233971L;

    /**
     * Construtor
     *
     * @since 1.0-SNAPSHOT
     */
    public NotFoundException() {super();}

    /**
     * Construtor
     *
     * @param cause
     *
     * @since 1.0-SNAPSHOT
     */
    public NotFoundException(Throwable cause) {super(cause);}

    /**
     * Construtor
     *
     * @param mensagem
     *
     * @since 1.0-SNAPSHOT
     */
    public NotFoundException(String mensagem) {super(mensagem);}

    /**
     * Construtor
     *
     * @param mensagem
     * @param cause
     *
     * @since 1.0-SNAPSHOT
     */
    public NotFoundException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
