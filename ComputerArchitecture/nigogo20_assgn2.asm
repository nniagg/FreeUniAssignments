.data
    N: .word 0
    M: .word 0
    .align 2          
occurrences: .space 408  

.text
    main:
        # Prompt for N and M
        li $v0, 4        
        la $a0, prompt_N
        syscall

        li $v0, 5    
        syscall
        move $t0, $v0     

        li $v0, 4   
        la $a0, prompt_M
        syscall

        li $v0, 5        
        syscall
        move $t1, $v0     

        # Initialize occurrences array to 0
        li $t2, 0        
    loop_init:
        beq $t2, 102, loop_init_done  
        sll $t3, $t2, 2              
        la $t4, occurrences          
        add $t3, $t3, $t4            
        sw $zero, 0($t3)            
        addi $t2, $t2, 1            
        j loop_init

    loop_init_done:

        # Read array A and count occurrences
        li $t3, 0        
    loop_read:
        beq $t3, $t0, loop_read_done 
        li $v0, 4          
        la $a0, prompt_A
        syscall

        li $v0, 5         
        syscall
        move $t4, $v0     

        # Check bounds of current element
        blt $t4, 1, loop_read      # if A[i] < 1
        bgt $t4, $t1, loop_read    # if A[i] > M
        # Calculate address of current element in occurrences array
        sll $t5, $t4, 2             
        la $t6, occurrences          
        add $t5, $t5, $t6            

        # Increment occurrence count
        lw $t7, 0($t5)   
        addi $t7, $t7, 1           
        sw $t7, 0($t5)   

        addi $t3, $t3, 1     
        j loop_read

    loop_read_done:

    loop_print_init:
        li $t6, 1          

    loop_print:
        bgt $t6, $t1, loop_print_done 

        # Print occurrence count
        la $t7, occurrences           
        sll $t8, $t6, 2              
        add $t8, $t8, $t7              
        lw $a0, 0($t8)                 
        li $v0, 1                      
        syscall

        # Print newline
        li $v0, 4                      
        la $a0, newline
        syscall

        addi $t6, $t6, 1                
        j loop_print

    loop_print_done:

        # Exit program
        li $v0, 10          
        syscall

    .data
    prompt_N: .asciiz "Enter the value of N: "
    prompt_M: .asciiz "Enter the value of M: "
    prompt_A: .asciiz "Enter element of array A: "
    newline: .asciiz "\n"
